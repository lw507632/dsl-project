package saucegang.dsl

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

class SauceGangDSL {

    // for pretty print purpose
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    // groovy shell and its configuration to parse and interpret script
    private GroovyShell shell
    private CompilerConfiguration configuration

    // definition of our binding and basescript
    private SauceGangBinding binding
    private SauceGangBasescript basescript

    SauceGangDSL() {
        // initialisation
        shell = new GroovyShell(configuration)
        configuration = getDSLConfiguration()


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
            staticStarImportsWhitelist = []
            //language tokens disallowed
//			tokensBlacklist= []
            //language tokens allowed
            tokensWhitelist = []
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList = [
                    int, Integer, Number, Integer.TYPE, String, Object
            ]
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList = [
                    int, Number, Integer, String, Object
            ]
        }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }


    void eval(File scriptFile) {
        println ANSI_GREEN + "SauceGangDSL::eval::IN -------------------------------------------------------" + ANSI_RESET

        Script script = shell.parse(scriptFile)

        // binding.setScript(script)
        // script.setBinding(binding)

        script.run()
        println ANSI_GREEN + "SauceGangDSL::eval::OUT -------------------------------------------------------" + ANSI_RESET

    }
}
