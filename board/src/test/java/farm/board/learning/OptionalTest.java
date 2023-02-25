package farm.board.learning;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void 변수가Null인경우(){
        //given
        String input = null;

        //when
        Optional<String> optionalInput = Optional.ofNullable(input);
        String result = optionalInput.orElse("input_null");

        //then
        Assertions.assertThat(result).isEqualTo("input_null");
    }
    @Test
    public void 변수가Null이아닌인경우(){
        //given
        String input = "input";

        //when
        Optional<String> optionalInput = Optional.ofNullable(input);
        String result = optionalInput.orElse("input_null");

        //then
        Assertions.assertThat(result).isEqualTo(input);
    }
}
