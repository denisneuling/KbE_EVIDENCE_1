package de.htw_berlin.aStudent.spring;

import org.apache.log4j.Logger;

/**
 * <p>
 * DeploymentModeProvider class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class DeploymentModeProvider {
	protected Logger log = Logger.getLogger(getClass());

	private String deploymentMode;

	/**
	 * <p>
	 * Constructor for DeploymentModeProvider.
	 * </p>
	 */
	public DeploymentModeProvider() {
		deploymentMode = System.getProperty("deploymentMode");
	}

	/**
	 * <p>
	 * Getter for the field <code>deploymentMode</code>.
	 * </p>
	 * 
	 * @return a object.
	 */
	public DeploymentMode getDeploymentMode() {
		try {
			return DeploymentMode.valueOf(this.deploymentMode);
		} catch (IllegalArgumentException iae) {
			return DeploymentMode.test;
		} catch (NullPointerException npe) {
			return DeploymentMode.test;
		}
	}

	public enum DeploymentMode {
		test, dev, stage, live;
	}
}
