package org.springframework.cli.util;

import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled("This is a WIP Test")
class ConversionUtilsTest {



    @DisplayName("Characterization Test to validate how this current works, note the xml declaration AND the duplicate dependencies element")
    @Disabled("This test exists to show what was actually coming out of ConversionUtil")
    @Test
    void existingFromDependencyListToString() {
        List<Dependency> dependencies = new ArrayList<>();
        Dependency dependency = new Dependency();
        dependency.setGroupId("org.springframework");
        dependency.setArtifactId("spring-core");
        dependency.setVersion("5.2.0.RELEASE");
        dependencies.add(dependency);
        String result = ConversionUtils.fromDependencyListToString(dependencies);
        assertThat(result.replaceAll("\\s",""))
                .isEqualTo("<?xmlversion=\"1.0\"encoding=\"UTF-8\"standalone=\"yes\"?>" +
                        "<dependencies>" +
                        "<dependencies>" +
                        "<artifactId>spring-core</artifactId>" +
                        "<groupId>org.springframework</groupId>" +
                        "<optional>false</optional>" +
                        "<version>5.2.0.RELEASE</version>" +
                        "</dependencies>" +
                        "</dependencies>");

    }

    @DisplayName("After ConversionUtils is corrected, this test should pass")
    @Test
    void validateFromDependencyListToString() {
        List<Dependency> dependencies = new ArrayList<>();
        Dependency dependency = new Dependency();
        dependency.setGroupId("org.springframework");
        dependency.setArtifactId("spring-core");
        dependency.setVersion("5.2.0.RELEASE");
        dependencies.add(dependency);
        String result = ConversionUtils.fromDependencyListToString(dependencies);
        assertThat(result.replaceAll("\\s",""))
                .isEqualTo("<dependencies>" +
                        "<dependency>" +
                        "<artifactId>spring-core</artifactId>" +
                        "<groupId>org.springframework</groupId>" +
                        "<version>5.2.0.RELEASE</version>" +
                        "</dependency>" +
                        "</dependencies>");

    }


    @DisplayName("fromDomToString removes XML declaration")
    @Test
    void fromDomToStringRemovesXmlDeclaration() {
        Xpp3Dom dom = new Xpp3Dom("root");
        Xpp3Dom child = new Xpp3Dom("child");
        child.setValue("value");
        dom.addChild(child);
        String result = ConversionUtils.fromDomToString(dom);
        assertThat(result).doesNotContain("<?xml").contains("<root>").contains("</root>").contains("<child>value</child>");
    }

    @DisplayName("fromDomToString handles empty Xpp3Dom")
    @Test
    void fromDomToStringHandlesEmptyXpp3Dom() {
        Xpp3Dom dom = new Xpp3Dom("root");
        String result = ConversionUtils.fromDomToString(dom);
        assertThat(result).isEqualTo("<root/>");
    }




}