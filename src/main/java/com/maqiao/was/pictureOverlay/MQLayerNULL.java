/**
 * 
 */
package com.maqiao.was.pictureOverlay;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQLayerNULL extends MQAbstractLayer {

	/**
	 * @param mqpo
	 * @param index
	 */
	public MQLayerNULL(MQPO mqpo, int index) {
		super(mqpo, index);
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isSafe()
	 */
	@Override
	public int isState() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#bufferImageFormat()
	 */
	@Override
	public void bufferImageFormat() {

	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isSafe()
	 */
	@Override
	public boolean isSafe() {
		return this.laterStage > 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MQLayerNULL [mqpo=");
		builder.append(mqpo);
		builder.append(", index=");
		builder.append(index);
		builder.append(", valid=");
		builder.append(valid);
		builder.append(", mainkey=");
		builder.append(mainkey);
		builder.append(", parameter=");
		builder.append(parameter);
		builder.append(", isFormat=");
		builder.append(isFormat);
		builder.append(", isBackground=");
		builder.append(isBackground);
		builder.append(", laterStage=");
		builder.append(laterStage);
		builder.append(", buffImg=");
		builder.append(buffImg);
		builder.append(", iscache=");
		builder.append(iscache);
		builder.append(", issave=");
		builder.append(issave);
		builder.append("]");
		return builder.toString();
	}

}
