/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.task.service;

import java.util.List;

import cn.edu.bjtu.auc.BaseService;
import cn.edu.bjtu.auc.StudentInfo;

/**
 * @author QiaoJian
 *
 */
public class RutgersNewJerseyService extends BaseService {

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.auc.BaseService#collecte(java.lang.String)
	 */
	@Override
	public List<StudentInfo> collecte(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getUrl(String firstName,String lastName){
		String url = "https://www.acs.rutgers.edu/pls/pdb_p/Pdb_Display.search_results?"
				+ "p_name_first={firstName}&p_name_last={lastName}";
		return url.replace("{firstName}", firstName).replace("{lastName}", lastName);
	}
}
