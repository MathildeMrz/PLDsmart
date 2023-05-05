module.exports = {
    networks: {
        development: {
            host: "127.0.0.1",
            port: 7545, // Update this port number if your Ganache is running on a different port
            network_id: "*", // Match any network id
        },
    },

    compilers: {
        solc: {
            version: "0.8.0", // Make sure to match the Solidity version in your smart contract
        },
    },
};