pragma solidity ^0.4.16;

contract Math {
    uint256 counter = 5; //state variable we assigned earlier

    function add() public { //increases counter by 1
        counter++;
    }

    function subtract() public { //decreases counter by 1
        counter--;
    }

}

var mathContract = web3.eth.contract([{"constant":false,"inputs":[],"name":"add","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[],"name":"subtract","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"}]);

personal.unlockAccount(eth.accounts[0])
personal.unlockAccount("0xca35b7d915458ef540ade6068dfe2f44e8fa733c")

var math = mathContract.new(
   {
     from: web3.eth.accounts[0],
     data: '0x6080604052600560005534801561001557600080fd5b5060c9806100246000396000f3006080604052600436106049576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f2be91f14604e5780636deebae3146062575b600080fd5b348015605957600080fd5b5060606076565b005b348015606d57600080fd5b5060746089565b005b6000808154809291906001019190505550565b6000808154809291906001900391905055505600a165627a7a72305820c2c79411b09d92bc479fc62ad81efaa8c8b08f48c9a96e6b115b3f4f02c1a2fd0029',
     gas: '2100000'
   }, function (e, contract){
    console.log(e, contract);
    if (typeof contract.address !== 'undefined') {
         console.log('Contract mined! address: ' + contract.address + ' transactionHash: ' + contract.transactionHash);
    }
 })

loadScript("test/math.abi")

loadScript("test/math.bin")


[
	{
		"constant": false,
		"inputs": [],
		"name": "add",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [],
		"name": "subtract",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	}
]

[{
	"linkReferences": {},
	"object": "6080604052600560005534801561001557600080fd5b5060c9806100246000396000f3006080604052600436106049576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634f2be91f14604e5780636deebae3146062575b600080fd5b348015605957600080fd5b5060606076565b005b348015606d57600080fd5b5060746089565b005b6000808154809291906001019190505550565b6000808154809291906001900391905055505600a165627a7a72305820c2c79411b09d92bc479fc62ad81efaa8c8b08f48c9a96e6b115b3f4f02c1a2fd0029",
	"opcodes": "PUSH1 0x80 PUSH1 0x40 MSTORE PUSH1 0x5 PUSH1 0x0 SSTORE CALLVALUE DUP1 ISZERO PUSH2 0x15 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0xC9 DUP1 PUSH2 0x24 PUSH1 0x0 CODECOPY PUSH1 0x0 RETURN STOP PUSH1 0x80 PUSH1 0x40 MSTORE PUSH1 0x4 CALLDATASIZE LT PUSH1 0x49 JUMPI PUSH1 0x0 CALLDATALOAD PUSH29 0x100000000000000000000000000000000000000000000000000000000 SWAP1 DIV PUSH4 0xFFFFFFFF AND DUP1 PUSH4 0x4F2BE91F EQ PUSH1 0x4E JUMPI DUP1 PUSH4 0x6DEEBAE3 EQ PUSH1 0x62 JUMPI JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST CALLVALUE DUP1 ISZERO PUSH1 0x59 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0x60 PUSH1 0x76 JUMP JUMPDEST STOP JUMPDEST CALLVALUE DUP1 ISZERO PUSH1 0x6D JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0x74 PUSH1 0x89 JUMP JUMPDEST STOP JUMPDEST PUSH1 0x0 DUP1 DUP2 SLOAD DUP1 SWAP3 SWAP2 SWAP1 PUSH1 0x1 ADD SWAP2 SWAP1 POP SSTORE POP JUMP JUMPDEST PUSH1 0x0 DUP1 DUP2 SLOAD DUP1 SWAP3 SWAP2 SWAP1 PUSH1 0x1 SWAP1 SUB SWAP2 SWAP1 POP SSTORE POP JUMP STOP LOG1 PUSH6 0x627A7A723058 KECCAK256 0xc2 0xc7 SWAP5 GT 0xb0 SWAP14 SWAP3 0xbc 0x47 SWAP16 0xc6 0x2a 0xd8 0x1e STATICCALL 0xa8 0xc8 0xb0 DUP16 0x48 0xc9 0xa9 PUSH15 0x6B115B3F4F02C1A2FD002900000000 ",
	"sourceMap": "26:244:0:-;;;64:1;46:19;;26:244;8:9:-1;5:2;;;30:1;27;20:12;5:2;26:244:0;;;;;;;"
}]
