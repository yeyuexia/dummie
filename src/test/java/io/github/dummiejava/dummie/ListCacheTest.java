package io.github.dummiejava.dummie;

import static io.github.dummiejava.dummie.Dummie.create;
import static io.github.dummiejava.dummie.Dummie.withStrategy;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.github.dummiejava.dummie.configuration.GenerationStrategy;
import java.util.List;
import org.junit.Test;

public class ListCacheTest {

  @Test
  public void should_create_object() {
    NestingData1 nestingData1 = create(NestingData1.class);

    validate(nestingData1);
  }

  @Test(expected = StackOverflowError.class)
  public void should_throw_stack_over_flow_with_random_strategy_and_cycle_dependency() {
    withStrategy(GenerationStrategy.RANDOM).create(NestingData1.class);
  }

  private void validate(NestingData1 nestingData1) {
    assertThat(nestingData1, not(nullValue()));
    assertThat(nestingData1.getNestingData2s(), not(nullValue()));
    assertThat(nestingData1.getNestingData2s().get(0), not(nullValue()));
    assertThat(nestingData1.getNestingData2s().get(0).getData(), not(nullValue()));
  }

  public static class NestingData1 {

    private String name;
    private List<NestingData2> nestingData2s;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<NestingData2> getNestingData2s() {
      return nestingData2s;
    }

    public void setNestingData2s(List<NestingData2> nestingData2s) {
      this.nestingData2s = nestingData2s;
    }
  }

  public static class NestingData2 {

    private String desc;
    private NestingData3 data;

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public NestingData3 getData() {
      return data;
    }

    public void setData(NestingData3 data) {
      this.data = data;
    }
  }

  public static class NestingData3 {

    private String desc;
    private List<NestingData1> data;

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public List<NestingData1> getData() {
      return data;
    }

    public void setData(List<NestingData1> data) {
      this.data = data;
    }
  }
}
