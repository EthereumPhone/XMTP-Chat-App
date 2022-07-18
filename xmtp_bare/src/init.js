
 const {Client} = require('@xmtp/xmtp-js')
 const {Wallet} = require('ethers')
 let wallet;


 if (localStorage.getItem("wallet") === null) {
   wallet = Wallet.createRandom()
   
   localStorage.setItem("wallet", wallet.privateKey)
 } else {
   wallet = new Wallet(localStorage.getItem("wallet"))
 }

 