package arguments.providers;

import arguments.holders.BoardIdValidationArgumentsHolder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Map;
import java.util.stream.Stream;

public class BoardIdValidationArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream provideArguments(ExtensionContext context) {
        return Stream.of(
                new BoardIdValidationArgumentsHolder(
                        Map.of("id", "invalid"),
                        "invalid id",
                        400
                ),
                new BoardIdValidationArgumentsHolder(
                        Map.of("id", "60d847d9aad2437cb984f8e1"),
                        "The requested resource was not found.",
                        404
                )
        ).map(Arguments::of);
    }
}
