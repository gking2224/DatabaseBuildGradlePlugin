package me.gking2224.dbgp.plugin

import static org.junit.Assert.*;

import org.junit.Test;

class ProfileTest {

    @Test
    public void test() {
        
        Profile parent = new Profile()
        Profile child = new Profile(parent)
        parent.url = "url"
        assertEquals(parent.url, child.url)
    }

}
