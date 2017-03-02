package com.fasterxml.jackson.datatype.jdk8;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OptionalBooleanTest extends ModuleTestBase
{
    static class BooleanBean {
        public Optional<Boolean> value;

        public BooleanBean() { }
        public BooleanBean(Boolean b) {
            value = Optional.ofNullable(b);
        }
    }

    private final ObjectMapper MAPPER = mapperWithModule();

    // for [datatype-jdk8#23]
    public void testBoolean() throws Exception
    {
        // First, serialization
        String json = MAPPER.writeValueAsString(new BooleanBean(true));
        assertEquals(aposToQuotes("{'value':true}"), json);
        json = MAPPER.writeValueAsString(new BooleanBean());
        assertEquals(aposToQuotes("{'value':null}"), json);
        json = MAPPER.writeValueAsString(new BooleanBean(null));
        assertEquals(aposToQuotes("{'value':null}"), json);

        // then deser
        BooleanBean b = MAPPER.readValue(aposToQuotes("{'value':null}"), BooleanBean.class);
        assertNotNull(b.value);
        assertFalse(b.value.isPresent());

        b = MAPPER.readValue(aposToQuotes("{'value':false}"), BooleanBean.class);
        assertNotNull(b.value);
        assertTrue(b.value.isPresent());
        assertFalse(b.value.get().booleanValue());

        b = MAPPER.readValue(aposToQuotes("{'value':true}"), BooleanBean.class);
        assertNotNull(b.value);
        assertTrue(b.value.isPresent());
        assertTrue(b.value.get().booleanValue());
    }
}
