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

}
