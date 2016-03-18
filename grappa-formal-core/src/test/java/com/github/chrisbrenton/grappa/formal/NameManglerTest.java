package com.github.chrisbrenton.grappa.formal;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class NameManglerTest
{
    private NameMangler mangler;

    @BeforeMethod
    public void init()
    {
        mangler = new NameMangler();
    }

    @Test
    public void nameConflictResolutionWorks()
    {
        final String name1 = mangler.toMethodName("x-y");
        final String name2 = mangler.toMethodName("x+y");
        final String name3 = mangler.toMethodName("x/y");

        assertThat(name1).isEqualTo("x_y");
        assertThat(name2).isEqualTo("x_y1");
        assertThat(name3).isEqualTo("x_y2");
    }
}
