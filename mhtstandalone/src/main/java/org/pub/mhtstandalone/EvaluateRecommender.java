/**
 * 
 */
package org.pub.mhtstandalone;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * @author wuhx Nov 21, 2017 11:45:36 PM
 *
 */
public class EvaluateRecommender {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TasteException 
	 */
	public static void main(String[] args) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("datafile/ntest.0.csv"));
		RecommenderBuilder builder = new MyRecommenderBuilder();
		
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		double result = evaluator.evaluate(builder, null, model, 0.7, 1.0);
		System.out.println(result);

		RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
		IRStatistics stats = statsEvaluator.evaluate(builder,null, model, null, 4, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);
		System.out.println(stats.getPrecision());
		System.out.println(stats.getRecall());		
	}
	
	private static class MyRecommenderBuilder implements RecommenderBuilder{

		/* (non-Javadoc)
		 * @see org.apache.mahout.cf.taste.eval.RecommenderBuilder#buildRecommender(org.apache.mahout.cf.taste.model.DataModel)
		 */
		@Override
		public Recommender buildRecommender(DataModel model) throws TasteException {
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(4,similarity, model);
			return new GenericUserBasedRecommender(model, neighborhood, similarity);				
		}
		
		
	}

}
