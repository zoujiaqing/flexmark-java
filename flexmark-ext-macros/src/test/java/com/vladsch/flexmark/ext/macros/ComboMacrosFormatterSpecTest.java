package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboMacrosFormatterSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/ext_macros_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            //.set(FormattingRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Arrays.asList(MacrosExtension.create(), GitLabExtension.create(), TablesExtension.create()))
            .set(GitLabExtension.RENDER_BLOCK_MATH, false)
            .set(GitLabExtension.RENDER_BLOCK_MERMAID, false)
            .set(GitLabExtension.DEL_PARSER, false)
            .set(GitLabExtension.INS_PARSER, false)
            .set(GitLabExtension.RENDER_VIDEO_IMAGES, false)
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .set(Parser.BLANK_LINES_IN_AST, true)
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        //optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        //optionsMap.put("option1", new MutableDataSet().set(FormatterExtension.FORMATTER_OPTION1, true));
        optionsMap.put("references-as-is", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_PLACEMENT, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_PLACEMENT, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_PLACEMENT, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_PLACEMENT, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM));
        //optionsMap.put("references-no-sort", new MutableDataSet().set(MacrosExtension.FOOTNOTE_PLACEMENTElementPlacementSort.AS_IS));
        optionsMap.put("references-sort", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_SORT, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(MacrosExtension.MACRO_DEFINITIONS_SORT, ElementPlacementSort.SORT_UNUSED_LAST));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final Formatter RENDERER = Formatter.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboMacrosFormatterSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public Formatter renderer() {
        return RENDERER;
    }
}
