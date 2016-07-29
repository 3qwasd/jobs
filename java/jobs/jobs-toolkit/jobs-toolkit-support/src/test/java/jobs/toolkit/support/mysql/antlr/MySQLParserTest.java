package jobs.toolkit.support.mysql.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class MySQLParserTest {
	
	public static void main(String[] args){
		ANTLRInputStream input = new ANTLRInputStream("create database");

		MySQLLexer lexer = new MySQLLexer(input);
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		MySQLParser parser = new MySQLParser(tokens);
		
		ParseTree tree = parser.stat();
		
		System.out.println(tree.toStringTree(parser));
		
		ParseTreeWalker walker = new ParseTreeWalker();
		
		walker.walk(new MySQLParserBaseListener(){
			
		}, tree);
	}
}
