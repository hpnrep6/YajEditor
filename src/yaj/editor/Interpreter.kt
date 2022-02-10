package yaj.editor
import yaj.YajInterpreter

class InterpreterCreator {
    /**
     * Create am interpreter object to be used to run code.
     */
    fun createInterpreter(source: String): Interpreter {
        return Interpreter(source);
    }
}

/**
 * Class to store output of interpreter as strings.
 */
class Interpreter(source: String): YajInterpreter(source) {
    val output: StringBuilder = StringBuilder()
    val errors: StringBuilder = StringBuilder()

    override fun out(output: String) {
        this.output.append(output, "\n");
    }

    override fun errorOut(error: String) {
        errors.append(error, "\n");
    }

    /**
     * Get output from executing code.
     */
    public fun getOutput(): String {
        return output.toString()
    }

    /**
     * Get errors from executing code.
     */
    public fun getErrors(): String {
        return errors.toString()
    }

    /**
     * Get both errors and output from executing code.
     */
    public fun getCombinedOutput(): String {
        if (getErrors().length <= 1) {
            return "Output:\n" + getOutput();
        }
        return "Errors: \n" + getErrors() + "\n================\n Output:\n" + getOutput()
    }
}