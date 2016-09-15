package spark.template.freemaker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Semih Okan Pehlivan
 */
public class FreeMakerTemplateEngine extends TemplateEngine {

    private Configuration configuration;

    public FreeMakerTemplateEngine() {
        this.configuration = createFreeMakerConfiguration();
    }

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate(modelAndView.getViewName());
            template.process(modelAndView.getModel(), stringWriter);

            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Configuration createFreeMakerConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreeMakerTemplateEngine.class, "");
        return configuration;
    }
}
