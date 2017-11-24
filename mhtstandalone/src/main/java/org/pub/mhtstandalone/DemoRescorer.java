/**
 * 
 */
package org.pub.mhtstandalone;

import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.common.LongPair;

/**
 * @author wuhx Nov 21, 2017 6:22:19 PM
 *
 */
public class DemoRescorer implements Rescorer<LongPair> {

	/* (non-Javadoc)
	 * @see org.apache.mahout.cf.taste.recommender.Rescorer#rescore(java.lang.Object, double)
	 */
	@Override
	public double rescore(LongPair thing, double originalScore) {
		//System.out.println("rescore:"+thing);
		return originalScore;
	}

	/* (non-Javadoc)
	 * @see org.apache.mahout.cf.taste.recommender.Rescorer#isFiltered(java.lang.Object)
	 */
	@Override
	public boolean isFiltered(LongPair thing) {
		//System.out.println("isFiltered:"+thing);
		// TODO Auto-generated method stub
		return false;
	}

}
