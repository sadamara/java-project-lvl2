package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<String> {

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public String call() throws Exception {
        String diff = Differ.generate(filepath1, filepath2);
        System.out.println(diff);
        return null;
    }

    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filepath2;

    @Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]", paramLabel = "format")
    private String format;




}


