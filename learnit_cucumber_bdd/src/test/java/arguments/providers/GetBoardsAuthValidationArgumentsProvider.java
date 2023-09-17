package arguments.providers;

import arguments.holders.AuthValidationArgumentsHolder;
import consts.UrlParamValues;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

public class GetBoardsAuthValidationArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream provideArguments(ExtensionContext context) {
        return Stream.of(
                new AuthValidationArgumentsHolder(
                        Collections.emptyMap(),
                        "unauthorized permission requested"),
                new AuthValidationArgumentsHolder(
                        Map.of("key", UrlParamValues.VALID_KEY),
                        "unauthorized permission requested"),
                new AuthValidationArgumentsHolder(
                        Map.of("token", UrlParamValues.VALID_TOKEN),
                        "invalid key")
        ).map(Arguments::of);
    }
}
