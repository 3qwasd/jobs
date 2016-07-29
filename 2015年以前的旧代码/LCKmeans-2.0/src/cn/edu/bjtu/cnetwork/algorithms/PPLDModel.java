/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.qj.graph.component.Edge;

/**
 * @author QiaoJian
 *
 */
public class PPLDModel {
	public static final String SAVE_MODEL_PATH = "I:\\network\\";
	Network network;
	public double alpha = 1;
	public double[][] s;//网络邻接矩阵
	public int N,E,K;//节点数,链接数,社区数
	public double[][] gamma,zeta_ik,m_zeta_ik; //size:N*K 节点的社团分布
	public double a[],b[],c[]; //参数 a,b,c
	public TriadMap<double[]> q; //q
	public double[][] n_in_ik,n_out_ik; // size:N*K
	public double[] n_in_i,n_out_i,m_zeta_i;//size:N
	public double[] m_k,eta_k,tao_k,zeta_k; //size:K
	public double c_params;
	public double tol = 1e-5;
	public int maxit = 1000;
	public int iterNum = 1;
	public double lastLikelihood = 0;
	public double currLikelihood = 0;
	
	/**
	 * @param network
	 */
	public PPLDModel(Network network) {
		super();
		this.network = network;
		N = network.nodeNum;
		E = network.edgeNum;
		K = network.topicNum;
	}

	public void init(){
		Random random = new Random();
		s = new double[N][N];
		gamma = new double[N][K];
		zeta_ik = new double[N][K];
		m_zeta_ik = new double[N][K];
		n_in_ik = new double[N][K];
		n_out_ik = new double[N][K];
		a = new double[N];
		b = new double[N];
		c = new double[N];
		n_in_i = new double[N];
		n_out_i = new double[N];
		m_zeta_i = new double[N];
		m_k = new double[K];
		eta_k = new double[K];
		tao_k = new double[K];
		zeta_k = new double[K];
		c_params = 0;
		q = new TriadMap<>();
		Iterator<Edge> edges = network.getEdges().iterator();
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
			int i = edge.getSourceNode().getId()-1;
			int j = edge.getTargetNode().getId()-1;
			if(i == j) { s[i][j] = 0.0d ; continue ;}
			else s[i][j] = 1.0d/E;
			double[] qijk = new double[K];
			q.put(i, j, qijk);
		}
		double a_sum = 0d;
		double b_sum = 0d;
		double c_sum = 0d;
		double gamma_i[] = new double[N]; 
		for(int i=0;i<N;i++){
			a[i] = random.nextDouble();
			b[i] = random.nextDouble();
			c[i] = random.nextDouble();
			a_sum+=a[i];
			b_sum+=b[i];
			c_sum+=c[i];
			for(int k=0;k<K;k++){
				gamma[i][k] = random.nextDouble();
				gamma_i[i]+=gamma[i][k];
			}
			
		}
		for(int i=0;i<N;i++){
			a[i] = a[i]/a_sum;
			b[i] = b[i]/b_sum;
			c[i] = c[i]/c_sum;
			for(int k=0;k<K;k++){
				gamma[i][k] = gamma[i][k]/gamma_i[i];
			}
			
		}
	}
	public void resetParams(){
		c_params = 0;
		for(int i=0;i<N;i++){
			n_in_i[i] = 0;
			n_out_i[i] = 0;
			m_zeta_i[i] = 0;
			for(int k=0;k<K;k++){
				zeta_ik[i][k] = 0;
				m_zeta_ik[i][k] = 0;
				n_in_ik[i][k] = 0;
				n_out_ik[i][k] = 0;
			}
		}
		for(int k=0;k<K;k++){
			m_k[k] = 0;
		}
	}
	public void computeQijk(){
		Iterator<Edge> edges = network.getEdges().iterator();
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
			int i = edge.getSourceNode().getId()-1;
			int j = edge.getTargetNode().getId()-1;
			double[] qij = q.get(i,j);
			double qijk_sum = 0.0d;
			for(int k=0;k<K;k++){
				qij[k] = ((gamma[i][k]*a[i])/(eta_k[k]))*
						((gamma[j][k]*b[j])/(tao_k[k]))*zeta_k[k];
				qijk_sum+=qij[k];
				
			}
			for(int k=0;k<K;k++){
				qij[k] = qij[k]/qijk_sum;
				n_in_ik[j][k] +=s[i][j]*qij[k];
				n_out_ik[i][k] += s[i][j]*qij[k];
				n_in_i[j]+=s[i][j]*qij[k];;
				n_out_i[i]+=s[i][j]*qij[k];
				m_k[k]+=s[i][j]*qij[k];
			}
		}
	}
	public void computeKParams(){
		for(int k=0;k<K;k++){
			eta_k[k] = 0;
			tao_k[k] = 0;
			zeta_k[k] = 0;
		}
		for(int i=0;i<N;i++){
			for(int k=0;k<K;k++){
				eta_k[k]+=gamma[i][k]*a[i];
				tao_k[k]+=gamma[i][k]*b[i];
				zeta_k[k]+=gamma[i][k]*c[i];
			}
		}
	}
	public void computeZetaParams(){
		for(int i=0;i<N;i++){
			for(int k=0;k<K;k++){
				zeta_ik[i][k] = gamma[i][k]*c[i]/(zeta_k[k]);
				m_zeta_ik[i][k] = (gamma[i][k]*c[i]/(zeta_k[k]))*m_k[k];
				m_zeta_i[i]+=m_zeta_ik[i][k];
			}
			c_params+=m_zeta_i[i]+alpha;
		}
	}
	public void computeMainParams(){
		double[] a_params = new double[N];
		double[] b_params = new double[N];
		double[] gamma_i = new double[N];
		double a_sum = 0;
		double b_sum = 0;
		double c_sum = 0;
		for(int i=0;i<N;i++){
			c[i] = (m_zeta_i[i]+alpha)/c_params;
			for(int k=0;k<K;k++){
				a_params[i]+=(m_k[k]/(eta_k[k]))*gamma[i][k];
				b_params[i]+=(m_k[k]/(tao_k[k]))*gamma[i][k];
				
			}
		}
		
		for(int i=0;i<N;i++){
			a[i] = n_out_i[i]/a_params[i];
			b[i] = n_in_i[i]/b_params[i];
			a_sum += a[i];
			b_sum += b[i];
			c_sum += c[i];
			for(int k=0;k<K;k++){
				gamma[i][k] = (n_in_ik[i][k]+n_out_ik[i][k]+m_zeta_ik[i][k])/
						((m_k[k]/eta_k[k])*a[i]+
						 (m_k[k]/tao_k[k])*b[i]+
						  m_zeta_i[i]);
				gamma_i[i]+=gamma[i][k];
			}
		}
		for(int i=0;i<N;i++){
			a[i] = a[i]/a_sum;
			b[i] = b[i]/b_sum;
			c[i] = c[i]/c_sum;
			int communityId = 0;
			double max_gamma = 0;
			for(int k=0;k<K;k++){
				gamma[i][k] = gamma[i][k]/gamma_i[i];
				if(gamma[i][k]>max_gamma){
					max_gamma = gamma[i][k];
					communityId = k;
				}
			}
			network.getNode(i).putAttribute("ppld_cid", communityId);
		}
	}
	public void computeLikelihood(){
		Iterator<Edge> edges = network.getEdges().iterator();
		lastLikelihood = currLikelihood;
		currLikelihood = 0d;
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
			int i = edge.getSourceNode().getId()-1;
			int j = edge.getTargetNode().getId()-1;
			double logterm = 0;
			for(int k=0;k<K;k++){
				logterm += (gamma[i][k]*a[i]/eta_k[k])*(gamma[j][k]*b[j]/tao_k[k])*zeta_k[k];
			}
			currLikelihood+=s[i][j]*Math.log(logterm);
		}
	}
	public void savePPLD() throws IOException{
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
	public boolean isFinish(){
		if(currLikelihood>=lastLikelihood&&(currLikelihood-lastLikelihood<tol))
			return true;
		if(iterNum>=maxit)
			return true;
		return false;
	}
	public void execute(){
		this.init();
		this.iterNum = 0;
		this.resetParams();
		this.computeKParams();
		this.computeLikelihood();
		this.lastLikelihood = this.currLikelihood-1;
		while(!this.isFinish()){
			//System.out.println("iter num:"+this.iterNum+"\tlikelihood:"+this.currLikelihood);
			this.computeQijk();
			this.computeZetaParams();
			this.computeMainParams();
			this.computeKParams();
			this.computeLikelihood();
			this.resetParams();
			this.iterNum++;
		}
		
	}
	public void processResult(){
//		for(int i=0;i<N;i++){
//			network.getNode(i).putAttribute("ppld", gamma[i]);
//		}
		List<Community> communities = new ArrayList<>();
		for(int k=0;k<K;k++){
			Community community = new Community();
			community.id = k;
			communities.add(k,community);
		}
		
		for(int i=0;i<N;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			int id = (int) node.getAttribute("ppld_cid");
			communities.get(id).addNode(node);
		}
		network.setCommunities(communities);
	}
}
