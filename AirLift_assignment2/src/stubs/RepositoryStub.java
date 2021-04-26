package stubs;

import common.Parameters;

public class RepositoryStub {
	/**
	 * Repository server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Repository server port
	 * @serialField serverPort
	 */
	private int serverPort;

    /**
     * Repository stub instantiation
     */
    public RepositoryStub() {
			serverHostName = Parameters.repositoryHostName;
			serverPort = Parameters.repositoryPort;
    }
}
