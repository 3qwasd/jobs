/**
*
**/
package jgibblda;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * @author QiaoJian
 *
 */
public class RunLDA {
	
	public int k=10;
	
	public RunLDA(){
		
	}
	/**
	 * @param k
	 */
	public RunLDA(int k) {
		super();
		this.k = k;
	}
	public void run(String dir,String args[]){
		LDACmdOption option = new LDACmdOption();
		option.dir = dir;
		option.K = k;
		CmdLineParser parser = new CmdLineParser(option);
		try {
			if (args.length == 0){
				showHelp(parser);
				return;
			}
			
			parser.parseArgument(args);
			
			if (option.est || option.estc){
				Estimator estimator = new Estimator();
				estimator.init(option);
				estimator.estimate();
			}
			else if (option.inf){
				Inferencer inferencer = new Inferencer();
				inferencer.init(option);
				
				Model newModel = inferencer.inference();
			
				for (int i = 0; i < newModel.phi.length; ++i){
					//phi: K * V
					System.out.println("-----------------------\ntopic" + i  + " : ");
					for (int j = 0; j < 10; ++j){
						System.out.println(inferencer.globalDict.id2word.get(j) + "\t" + newModel.phi[i][j]);
					}
				}
			}
		}
		catch (CmdLineException cle){
			System.out.println("Command line error: " + cle.getMessage());
			showHelp(parser);
			return;
		}
		catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
	}
	public static void showHelp(CmdLineParser parser){
		System.out.println("LDA [options ...] [arguments...]");
		parser.printUsage(System.out);
	}
}
