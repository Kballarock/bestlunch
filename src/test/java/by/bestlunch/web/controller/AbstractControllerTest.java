package by.bestlunch.web.controller;

import by.bestlunch.persistence.model.User;
import by.bestlunch.util.MessageUtil;
import by.bestlunch.util.exception.ErrorType;
import by.bestlunch.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static by.bestlunch.web.controller.AbstractControllerTest.RequestWrapper.wrap;

@SpringJUnitWebConfig(AbstractControllerTest.Config.class)
@Transactional
public abstract class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Configuration
    @ComponentScan({"by.bestlunch"})
    static class Config {}

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String url;

    public AbstractControllerTest(String url) {
        this.url = url + '/';
    }

    @Autowired
    protected MessageUtil messageUtil;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    public ResultActions perform(RequestWrapper wrapper) throws Exception {
        return perform(wrapper.builder);
    }

    public ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected RequestWrapper doGet(String urlTemplatePad, Object... uriVars) {
        return wrap(MockMvcRequestBuilders.get(url + urlTemplatePad, uriVars));
    }

    protected RequestWrapper doGet() {
        return wrap(MockMvcRequestBuilders.get(url));
    }

    protected RequestWrapper doGet(String pad) {
        return wrap(MockMvcRequestBuilders.get(url + pad));
    }

    protected RequestWrapper doGet(int id) {
        return doGet("{id}", id);
    }

    protected RequestWrapper doDelete() {
        return wrap(MockMvcRequestBuilders.delete(url));
    }

    protected RequestWrapper doDelete(int id, String s) {
        return wrap(MockMvcRequestBuilders.delete(url + "{id}" + "{s}", id, s));
    }

    protected RequestWrapper doDelete(int id) {
        return wrap(MockMvcRequestBuilders.delete(url + "{id}", id));
    }

    protected RequestWrapper doPut() {
        return wrap(MockMvcRequestBuilders.put(url));
    }

    protected RequestWrapper doPut(int id) {
        return wrap(MockMvcRequestBuilders.put(url + "{id}", id));
    }

    protected RequestWrapper doPost(String pad) throws Exception {
        return wrap(MockMvcRequestBuilders.post(url + pad));
    }

    protected RequestWrapper doPost() {
        return wrap(MockMvcRequestBuilders.post(url));
    }

    protected RequestWrapper doPatch(int id) {
        return wrap(MockMvcRequestBuilders.patch(url + "{id}", id));
    }

    public static class RequestWrapper {
        private final MockHttpServletRequestBuilder builder;

        private RequestWrapper(MockHttpServletRequestBuilder builder) {
            this.builder = builder;
        }

        public static RequestWrapper wrap(MockHttpServletRequestBuilder builder) {
            return new RequestWrapper(builder);
        }

        public MockHttpServletRequestBuilder unwrap() {
            return builder;
        }

        public <T> RequestWrapper jsonBody(T body) {
            builder.contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(body));
            return this;
        }

        public RequestWrapper jsonUserWithPassword(User user) {
            builder.contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeAdditionProps(user, "password", user.getPassword()));
            return this;
        }

        public RequestWrapper basicAuth(User user) {
            builder.with(SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword()));
            return this;
        }

        public RequestWrapper auth(User user) {
            builder.with(SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())));
            return this;
        }
    }

    private String getMessage(String code) {
        return messageUtil.getMessage(code, MessageUtil.EN_LOCALE);
    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(getMessage(code));
    }
}