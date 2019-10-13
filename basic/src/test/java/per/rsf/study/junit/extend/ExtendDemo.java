package per.rsf.study.junit.extend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TimingExtension.class)
public class ExtendDemo {

    @Test
    void sleep20ms() throws Exception {
        Thread.sleep(20);
    }

}
