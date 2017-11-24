/**
 * 
 */
package src.main.java.org.pub.mhtstandalone;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/**
 * @author wuhx Nov 23, 2017 11:51:52 AM Preference Value按时间戳衰减的数据模型, 衰减算法为:
 *         initValue * (1/sqrt(dayof(timestamp-currenttimestamp)))
 */
public class TimestampFileDataModel extends FileDataModel {

	/**
	 * @param dataFile
	 * @throws IOException
	 */
	public TimestampFileDataModel(File dataFile) throws IOException {
		super(dataFile);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Float getPreferenceValue(long userID, long itemID) throws TasteException {
		Float initPerferenceValue = super.getPreferenceValue(userID, itemID);
		if (initPerferenceValue == null) {
			return initPerferenceValue;
		}
		Long initPreferenceTime = super.getPreferenceTime(userID, itemID);
		return recomputePreference(initPerferenceValue, initPreferenceTime);
	}

	@Override
	public PreferenceArray getPreferencesForItem(long itemID) throws TasteException {
		PreferenceArray initPreferenceArray = super.getPreferencesForItem(itemID);
		if (initPreferenceArray == null) {
			return initPreferenceArray;
		}
		final FileDataModel spModel = this;
		initPreferenceArray.forEach(new Consumer<Preference>() {
			@Override
			public void accept(Preference p) {
				Long initPreferenceTime = null;
				try {
					initPreferenceTime = spModel.getPreferenceTime(p.getUserID(), p.getItemID());
				} catch (TasteException e) {
					e.printStackTrace();
				}
				Float newPreferenceValue = recomputePreference(p.getValue(), 1L);
				p.setValue(newPreferenceValue);
			}

		});
		return initPreferenceArray;
	}

	/**
	 * 根据事件行为发生的时间戳离当前时间的远近来重新计算用户偏好值
	 * 
	 * @param initPreference
	 * @param initPreferenceTime
	 * @return
	 */
	private Float recomputePreference(Float initPreference, Long initPreferenceTime) {
		if (initPreference == null || initPreferenceTime == null) {
			return initPreference;
		}
		int days = (int) (System.currentTimeMillis() - initPreferenceTime) / (24 * 3600 * 1000);
		double sqrtDays = Math.sqrt(days * 1.0);
		Float newPerferenceValue = new Float(initPreference * (1.0 / sqrtDays));
		return newPerferenceValue;
	}

}
