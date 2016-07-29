/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.qj.graph.component.Edge;

/**
 * @author QiaoJian
 *
 */
@Deprecated
public class PPLD extends NetworkAlgorithms {
	public static final String SAVE_MODEL_PATH = "I:\\network\\";
	int[][] s;/* network.adjMaritx */
	public int K,N,E;//class num,node num,Edge num
	double alpha = 1;
	TriadMap<double[]> q;
	double[][] gamma,n_in,n_out;/*γ size:network.nodeNum*network.topicNum */
	double[] a,b,c,n_ins,n_outs,m_zeta;/* PPLD params size:network.nodeNum */
	double[] eta,tao,zeta,m;/*和变量 size：network.topicNum */
	double m_zetas;
	private void init(){
		s = network.adjMatrix;K = network.topicNum;N = network.nodeNum;E = network.edgeNum;
		q = new TriadMap<>();
		gamma = new double[N][K];n_in = new double[N][K];n_out = new double[N][K];
		a = new double[N];b = new double[N];c = new double[N];
		n_ins = new double[N];n_outs = new double[N];m_zeta = new double[N];
		eta = new double[K];tao = new double[K];zeta = new double[K];m = new double[K];
		for(int i=0;i<N;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			a[i] = node.getOutEdges().size()/(double)N;
			b[i] = node.getInEdges().size()/(double)N;
			c[i] = 1.0d/N;
			for(int k = 0;k<K;k++){
				gamma[i][k] = 1.0d/K;
				eta[k] += gamma[i][k]*a[i];
				tao[k] += gamma[i][k]*b[i];
				zeta[k] += gamma[i][k]*c[i];
			}
		}
		
		Iterator<Edge> edges = network.getEdges().iterator();
		
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
	
			int i = edge.getSourceNode().getId()-1;
			int j = edge.getTargetNode().getId()-1;
			double[] qij = new double[K];
			q.put(i, j, qij);
		}
		
	}
	private void eStep(){
		/*EM算法 E步*/
		Iterator<Edge> edges = network.getEdges().iterator();
		
		while(edges.hasNext()){
			//获取变量
			NetworkEdge edge = (NetworkEdge) edges.next();

			int i = edge.getSourceNode().getId()-1;
			int j = edge.getTargetNode().getId()-1;
			double[] qij = q.get(i, j);
			//初始化各变量
			
			for(int k=0;k<K;k++){
				//计算q_ijk
				qij[k] = ((gamma[i][k]*a[i])/(eta[k]-gamma[i][k]*a[i]))*((gamma[j][k]*b[j])/(tao[k]-gamma[j][k]*b[j]));
				//计算m_k
				m[k]+=qij[k];
				//计算n_out(i,k)
				n_out[i][k] += qij[k];
				//计算n_in(j,k)
				n_in[j][k] += qij[k];
				//计算n_out(i)
				n_outs[i] += n_out[i][k];
				//计算n_in(j)
				n_ins[j] += n_in[j][k];
			}
		}
		
	}
	private void mStep(){
		for(int i=0;i<N;i++){
			for(int k=0;k<K;k++){
				m_zeta[i]+=m[k]*(gamma[i][k]*c[i])/(zeta[k]-gamma[i][k]*c[i]);
			}
			m_zetas+=m_zeta[i];
		}
		for(int i=0;i<N;i++){
			double a_params = 0;
			double b_params = 0;
			double m_zeta_i = m_zeta[i];
			for(int k=0;k<K;k++){
				double eta_k = eta[k];
				double tao_k = tao[k];
				double zeta_k = zeta[k];
				double gamma_i_k = gamma[i][k];
				double zeta_i_k = gamma_i_k*c[i]/(zeta_k-gamma_i_k*c[i]);
				double m_zeta_i_k = zeta_i_k*m[k];
				double m_eta_k = m[k]/(eta_k-gamma_i_k*a[i]);
				double m_tao_k = m[k]/(tao_k-gamma_i_k*b[i]);
				/*计算 新的gamma[i][k]*/
				gamma[i][k] = (n_in[i][k]+n_out[i][k]+m_zeta_i_k)/(m_eta_k*a[i]+m_tao_k*b[i]+m_zeta_i);
				a_params += m_eta_k*gamma[i][k];
				b_params += m_tao_k*gamma[i][k];
			}
			a[i] = n_outs[i]/a_params;
			b[i] = n_ins[i]/b_params;
			c[i] = (m_zeta_i+E*alpha)/(m_zetas+N*E*alpha);
			
		}
	}
	private void prepareParams(){
		eta = new double[K];
		tao = new double[K];
		zeta = new double[K];
		m_zeta = new double[N];
		m = new double[K];
		m_zetas = 0;
		for(int i=0;i<N;i++){
			for(int k = 0;k<K;k++){
				eta[k] += gamma[i][k]*a[i];
				tao[k] += gamma[i][k]*b[i];
				zeta[k] += gamma[i][k]*c[i];
				n_in[i][k] = 0;
				n_out[i][k] = 0; 
			}
			n_ins[i] = 0;
			n_outs[i] = 0;
			 
		}
//		for(int i=0;i<N;i++){
//			for(int k=0;k<K;k++){
//				m_zeta[i]+=m[k]*(gamma[i][k]*c[i])/(zeta[k]-gamma[i][k]*c[i]);
//			}
//			m_zetas+=m_zeta[i];
//		}		
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		init();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		for(int i=0;i<20;i++){
			System.out.println("Iterat num:"+(i+1));
			eStep();
			mStep();
			prepareParams();
		}
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#afterExecute()
	 */
	@Override
	void afterExecute() {
		// TODO Auto-generated method stub
		super.afterExecute();
		for(int i=0;i<N;i++){
			network.getNode(i).putAttribute("ppld",gamma[i]);
		}
		try {
			savePPLD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void savePPLD() throws IOException{
		File file = new File(SAVE_MODEL_PATH+network.networkName+"\\PPLD.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<N;i++){
			for(int k=0;k<K;k++){
				bufferedWriter.write(gamma[i][k]+"\t");
			}
			bufferedWriter.write("\n");
		}
		bufferedWriter.close();
	}
	/**
	 * @param network
	 */
	public PPLD(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}
}
