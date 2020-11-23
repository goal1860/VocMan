package helpers;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.util.Arrays;
import java.util.List;

public class DiffHelper
{
    public static void main(String[] args) throws DiffException
    {
        //create a configured DiffRowGenerator
        DiffRowGenerator generator = DiffRowGenerator.create()
                        .showInlineDiffs(true)
                        .mergeOriginalRevised(true)
                        .inlineDiffByWord(true)
                        .oldTag(f -> "~")      //introduce markdown style for strikethrough
                        .newTag(f -> "**")     //introduce markdown style for bold
                        .build();

        //compute the differences for two test texts.
        List<DiffRow> rows = generator.generateDiffRows(
                        Arrays.asList("This is a test senctence."),
                        Arrays.asList("This is a test for diffutils."));

        System.out.println(rows.get(0).getOldLine());
    }

    public static float calcScore(String str1, String str2) {
//        StringMetric metric =
//                        with(new CosineSimilarity<String>())
//                                        .simplify(Simplifiers.toLowerCase(Locale.ENGLISH))
//                                        .simplify(Simplifiers.replaceNonWord())
//                                        .tokenize(Tokenizers.whitespace())
//                                        .build();
//
//        return metric.compare(str1, str2); //0.5720
        return 0;
    }

    public static String diff(String str1, String str2)
    {
        //create a configured DiffRowGenerator
        DiffRowGenerator generator = DiffRowGenerator.create()
                        .showInlineDiffs(true)
                        .mergeOriginalRevised(true)
                        .inlineDiffByWord(true)
                        .oldTag(f -> "~")      //introduce markdown style for strikethrough
                        .newTag(f -> "*")     //introduce markdown style for bold
                        .build();

        //compute the differences for two test texts.
        List<DiffRow> rows = null;
        try {
            rows = generator.generateDiffRows(
                            Arrays.asList(str1),
                            Arrays.asList(str2));
        } catch (DiffException e) {
            throw new RuntimeException(e);
        }
        String beforeProcess = rows.get(0).getOldLine();
        boolean f1 = false;
        boolean f2 = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < beforeProcess.length(); i++) {
            if(beforeProcess.charAt(i) == '~') {
                if(f1) {
                    sb.append("</del>");
                }else {
                    sb.append("<del>");
                }
                f1 = !f1;
            }else if(beforeProcess.charAt(i) == '*') {
                if(f1) {
                    sb.append("</strong>");
                }else {
                    sb.append("<strong>");
                }
                f2 = !f2;
            } else {
                sb.append(beforeProcess.charAt(i));
            }
        }


        return sb.toString();
    }
}
