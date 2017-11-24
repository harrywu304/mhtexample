/**
 * 
 */
package src.main.java.org.pub.mhtstandalone;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

/**
 * @author wuhx Nov 21, 2017 6:32:53 PM
 *
 */
public class DemoIDRescorer implements IDRescorer {

	/* (non-Javadoc)
	 * @see org.apache.mahout.cf.taste.recommender.IDRescorer#rescore(long, double)
	 */
	@Override
	public double rescore(long id, double originalScore) {
		System.out.println("rescore:"+id);
//		if(id==14) {
//			originalScore++;
//		}
		return originalScore;
	}

	/* (non-Javadoc)
	 * @see org.apache.mahout.cf.taste.recommender.IDRescorer#isFiltered(long)
	 */
	@Override
	public boolean isFiltered(long id) {
		System.out.println("isFiltered:"+id);
		return false;
	}

}
