package com.repocleaner.coreclean.languages.tregex;

//import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.trees.tregex.TregexPattern;
//import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
//import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class Main {
//    public static void main(String[] args) {
//        Tree t = Tree.valueOf("(ROOT (S (NP (NP (NNP Bank)) (PP (IN of) (NP (NNP America)))) (VP (VBD used) (S (VP (TO to) (VP (VB be) (VP (VBN called) (NP (NP (NNP Bank)) (PP (IN of) (NP (NNP Italy)))))))))))");
//        TregexPattern pat = TregexPattern.compile("NP <1 (NP << Bank) <2 PP=remove");
//        TsurgeonPattern surgery = Tsurgeon.parseOperation("excise remove remove");
//        t.pennPrint();
//        Tsurgeon.processPattern(pat, surgery, t).pennPrint();
//        pat = TregexPattern.compile("S=a << NP");
//        surgery = Tsurgeon.parseOperation("excise a a");
//        Tsurgeon.processPattern(pat, surgery, t).pennPrint();
//    }
//    private static void test(){
//        long preCompile = System.nanoTime();
//        Grammar grammar = new JavaGrammarCreator().create();
//        ParseTreeFactory<TreeNode, TreeNode, TreeNode> parseTreeFactory = new TreeNodeParseTreeFactory();
//        long postCompile = System.nanoTime();
//        long preLex = System.nanoTime();
//        CharStream charStream = new FileCharStream(new File("C:/Users/Nathan/IdeaProjects/JavaParser/src/main/resources/Test.txt"));
//        CharStreamReader reader = new CharStreamReader(charStream);
//        Tree tree = null;
//        try {
//            tree = grammar.parse(reader, JavaParserRulesPackages.CompilationUnit, parseTreeFactory);
//        } catch (Throwable e) {
//            do {
//                System.out.println(e.getMessage());
//                for (Throwable suppressed : e.getSuppressed()) {
//                    System.out.println(suppressed.getMessage());
//                }
//                Throwable next = e.getCause();
//                e = (next == e)
//                        ? null
//                        : next;
//            } while (e != null);
//        }
//        System.out.println(tree);
//        System.out.println(tree.pennString());
//        System.exit(0);
//
//        long postLex = System.nanoTime();
//        double compileDiffNanos = postCompile - preCompile;
//        double compileDiffSeconds = compileDiffNanos * 1E-6;
//        System.out.println(compileDiffSeconds);
//        double lexDiffNanos = postLex - preLex;
//        double lexDiffSeconds = lexDiffNanos * 1E-6;
//        System.out.println(lexDiffSeconds);
//        System.out.println(tree.pennString());
//
//        TregexPattern regex = TregexPattern.compile("ClassBodyDeclaration_Member=a");
//        regex = TregexPattern.compile("ClassBodyDeclaration_Member=a <--. (MemberDeclaration .<-- (b not == a))");
//        TregexMatcher matcher = regex.matcher(tree);
//        List<Tree> trees = new ArrayList<>();
//        while(matcher.findNextMatchingNode()){
//            trees.add(matcher.getMatch());
//        }
//        for (int i = 1; i < trees.size(); i++) {
//            Tree subsequentTree = trees.get(i);
//            Tree parent = subsequentTree.parent();
//            int index = Arrays.asList(parent.children()).indexOf(subsequentTree);
//            subsequentTree.parent().removeChild(index);
//        }
//        System.out.println(tree.pennString());
//        TsurgeonPattern operation = Tsurgeon.parseOperation("excise a a");
//
//        tree = Tsurgeon.processPattern(regex, operation, tree);
//        String pennString = tree.pennString();
//        System.out.println(pennString);
//    }
//
//    private static String toString(File file) {
//        try {
//            byte[] encoded = Files.readAllBytes(file.toPath());
//            return new String(encoded, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
