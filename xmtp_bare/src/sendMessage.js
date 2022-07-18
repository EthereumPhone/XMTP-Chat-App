
 const {Client} = require('@xmtp/xmtp-js')
 const {Wallet} = require('ethers')
 let wallet;
 let xmtp;


 if (localStorage.getItem("wallet") === null) {
    wallet = Wallet.createRandom()
    localStorage.setItem("wallet", wallet.privateKey)
  } else {
    wallet = new Wallet(localStorage.getItem("wallet"))
  }
 

 
// A micro service will exit when it has nothing left to do.  So to
 // avoid a premature exit, set an indefinite timer.  When we
 // exit() later, the timer will get invalidated.
 
async function sendMessage(msg, target) { 
    // Create the client with your wallet. This will connect to the XMTP development network by default    
    // Start a conversation with Vitalik
    xmtp = await Client.create(wallet);
    const conversation = await xmtp.conversations.newConversation(target)
    // Send a message
    await conversation.send(msg)
    
 }

sendMessage("%message%", "%target%")