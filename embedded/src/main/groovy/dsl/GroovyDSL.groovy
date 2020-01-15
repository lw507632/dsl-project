package dsl

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import io.github.mosser.arduinoml.kernel.structural.SIGNAL

class GroovyDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private VariablesBinding binding
    private BaseScript basescript
    private BASE_SCRIPT_CLASSPATH = "dsl.BaseScript"
    //private BASE_SCRIPT_CLASSPATH = "main.groovy.dsl.BaseScript"

    GroovyDSL() {
        binding = new VariablesBinding()
        binding.setGroovuinoMLModel(new GroovyModel(binding));
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass(BASE_SCRIPT_CLASSPATH)
        shell = new GroovyShell(configuration)

        binding.setVariable("high", SIGNAL.HIGH)
        binding.setVariable("low", SIGNAL.LOW)
    }

    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()
        secure.with {
            //disallow closure creation
            closuresAllowed = false
            //disallow method definitions
            methodDefinitionAllowed = true
            //empty white list => forbid imports
            importsWhitelist = [
                    'java.lang.*'
            ]
            staticImportsWhitelist = []
            staticStarImportsWhitelist= []

            tokensWhitelist= []
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList= [
                    int, Integer, Number, Integer.TYPE, String, Object
            ]
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList= [
                    int, Number, Integer, String, Object
            ]
        }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)

        binding.setScript(script)
        script.setBinding(binding)

        script.run()
    }
}
