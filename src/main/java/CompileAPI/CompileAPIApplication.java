package CompileAPI;


import CompileAPI.resources.SubmitResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CompileAPIApplication extends Application<CompileAPIConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CompileAPIApplication().run(args);
    }

    @Override
    public String getName() {
        return "CompileAPI";
    }

    @Override
    public void initialize(final Bootstrap<CompileAPIConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CompileAPIConfiguration configuration,
                    final Environment environment) {
    	
    	// register the submission resource
    	final SubmitResource submitResource = new SubmitResource();
    	environment.jersey().register(submitResource);
    	
    }

}
