package org.pub.mhtstandalone;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserCF {

	public static void main(String[] args) throws IOException, TasteException {
		String file = "datafile/ntest.0.csv";
		//每一行的格式:userID,itemID[,preference[,timestamp]]
		DataModel model = new FileDataModel(new File(file));
		//DataModel model = new TimestampFileDataModel(new File(file));
		
		//需要preference值的相似度算法
		//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
		
		//不需要preference值的相似度算法
		//UserSimilarity similarity = new LogLikelihoodSimilarity(model);
		//UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);				
		System.out.println("user similarity:"+similarity.userSimilarity(4, 5));
		
		//create UserNeighborhood
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,similarity, model);
		//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.01, similarity, model);
		
		//create recommender
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		IDRescorer rescorer = new DemoIDRescorer();
		List<RecommendedItem> recommendations = recommender.recommend(5, 2, rescorer);
		for (RecommendedItem recommendation : recommendations) {
		  System.out.println(recommendation);
		}
		
		Thread updateThread = new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(70000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("updateThread todo refresh now...");
					recommender.refresh(null);
				}
			}
		};
		updateThread.start();
		
		Object lock = new Object();
		synchronized(lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
