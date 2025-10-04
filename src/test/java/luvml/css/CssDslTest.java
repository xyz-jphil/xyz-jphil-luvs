package luvml.css;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static luvml.css.P.*;
import static luvml.css.V.*;  // Single import for ALL CSS functions and type-safe values!

/**
 * Test suite for advanced CSS DSL features.
 */
public class CssDslTest {

    @Test
    public void testCalcArithmetic() {
        var result = percent(100).minus(px(20)).toString();
        assertEquals("calc(100% - 20px)", result);

        var complex = percent(100).minus(px(20)).divide(2).toString();
        assertEquals("calc(calc(100% - 20px) / 2)", complex);
    }

    @Test
    public void testCssVariables() {
        enum TestVars implements CssVariable {
            primary_color,
            spacing_unit;
        }

        var def = TestVars.primary_color.def("#007bff").toString();
        assertEquals("--primary-color: #007bff;", def);

        var ref = TestVars.primary_color.ref();
        assertEquals("var(--primary-color)", ref);

        var refWithFallback = TestVars.spacing_unit.ref(px(20));
        assertEquals("var(--spacing-unit, 20px)", refWithFallback);
    }

    @Test
    public void testTypeSafeColors() {
        var prop = color(RED).toString();
        assertEquals("color: red;", prop);

        var bg = background_color(TRANSPARENT).toString();
        assertEquals("background-color: transparent;", bg);
    }

    @Test
    public void testMathFunctions() {
        var minVal = min(percent(100), px(500));
        assertEquals("min(100%, 500px)", minVal);

        var clampVal = clamp(px(12), vw(2), px(24));
        assertEquals("clamp(12px, 2vw, 24px)", clampVal);
    }

    @Test
    public void testColorFunctions() {
        var rgbColor = rgb(255, 0, 0);
        assertEquals("rgb(255, 0, 0)", rgbColor);

        var rgbaColor = rgba(0, 0, 255, 0.5);
        assertEquals("rgba(0, 0, 255, 0.5)", rgbaColor);

        var hslColor = hsl(120, percent(100), percent(50));
        assertEquals("hsl(120, 100%, 50%)", hslColor);
    }

    @Test
    public void testGradients() {
        var linear = linearGradient(deg(45), "#ff0000", "#0000ff");
        assertEquals("linear-gradient(45deg, #ff0000, #0000ff)", linear);

        var radial = radialGradient("#ff0000", "#0000ff");
        assertEquals("radial-gradient(#ff0000, #0000ff)", radial);
    }

    @Test
    public void testTransforms() {
        var simple = rotate(deg(45)).toString();
        assertEquals("rotate(45deg)", simple);

        var chained = rotate(deg(45)).scale(1.5).translateX(px(10)).toString();
        assertEquals("rotate(45deg) scale(1.5) translateX(10px)", chained);
    }

    @Test
    public void testFilters() {
        var simple = blur(px(5)).toString();
        assertEquals("blur(5px)", simple);

        var chained = blur(px(5)).brightness(1.2).contrast(0.8).toString();
        assertEquals("blur(5px) brightness(1.2) contrast(0.8)", chained);
    }

    @Test
    public void testTypeSafePropertyValues() {
        var textAlignProp = text_align(CENTER).toString();
        assertEquals("text-align: center;", textAlignProp);

        var displayProp = display(FLEX).toString();
        assertEquals("display: flex;", displayProp);
    }

    @Test
    public void testSelectorChaining() {
        enum TestClass implements CssClass {
            container;
        }

        var selector = TestClass.container.child(HtmlTag.div).hover().before();
        assertEquals(".container > div:hover::before", selector.build());
    }

    @Test
    public void testAngleUnits() {
        var degrees = deg(90).toString();
        assertEquals("90deg", degrees);

        var radians = rad(1.57).toString();
        assertEquals("1.57rad", radians);
    }
}
