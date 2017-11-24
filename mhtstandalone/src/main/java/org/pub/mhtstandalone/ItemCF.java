/**
 * 
 */
package src.main.java.org.pub.mhtstandalone;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 * @author wuhx Nov 21, 2017 5:47:51 PM
 *
 */
public class ItemCF {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TasteException 
	 */
	public static void main(String[] args) throws IOException, TasteException {
		String file = "datafile/test.0.csv";
		DataModel model = new FileDataModel(new File(file));
		//TanimotoCoefficient算法不需要preference值
		ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
		ItemBasedRecommender recommender = new GenericBooleanPrefItemBasedRecommender(model, similarity);
		Rescorer rescorer = new DemoRescorer();
		
		List<RecommendedItem> recommendations = recommender.mostSimilarItems(15,4,rescorer);
		for (RecommendedItem recommendation : recommendations) {
			  System.out.println(recommendation);
		}
		
	}

}
