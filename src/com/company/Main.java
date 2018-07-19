package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String content;
    private boolean isWord;

    public TrieNode(){
        this.children = new HashMap<Character,TrieNode>();
        isWord = false;

    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, TrieNode> children) {
        this.children = children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

}

class Trie {
    private TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }
    public void insert(String word) {
        TrieNode current = root;

        for (Character c: word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(c, d -> new TrieNode());
        }
        current.setWord(true);
    }

    public List<String> suggestoinAsPrefix(String prefix){
        TrieNode node = validPrefix(prefix);
        List<String> result = new LinkedList<String>();
        if(node != null)
        {
            StringBuilder sb = new StringBuilder(prefix);
            result = recusivelyPrint(node,result,sb,prefix);
        }
        return result;
    }

    private List<String> recusivelyPrint(TrieNode node, List<String> result, StringBuilder sb, String prefix){
        if(node.isWord()) {
            result.add(result.size(), sb.toString());
        }
        for (Character c: node.getChildren().keySet()) {
            if(node.getChildren().size() > 0)
                recusivelyPrint(node.getChildren().get(c),result,sb.append(c),prefix);
            sb.deleteCharAt(sb.length()-1);
        }
        return result;
    }

    private TrieNode validPrefix(String s){
        TrieNode current = root;
        for (Character c: s.toCharArray()){
            if(current.getChildren().containsKey(c))
                current = current.getChildren().get(c);
            else
                return null;
        }
        return current;
    }


}


public class Main {

    public static void main(String[] args){
        Trie t = new Trie();
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        while(!s.isEmpty()){
            t.insert(s);
            s = scan.nextLine();
        }
        s = scan.nextLine();
        List<String> res = t.suggestoinAsPrefix(s);
        if(res.isEmpty())
            System.out.println("<NONE>");
        else
            for(int i = res.size()-1; i>= 0; i--)
                System.out.println(res.get(i));

    }
}
