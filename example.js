/*
 * Copyright © 2018-20 LiquidPlayer
 *
 * Released under the MIT license.
 * See https://github.com/LiquidPlayer/LiquidCore/LICENSE.md for terms.
 */

 /* Hello, World! */
const {LiquidCore} = require('liquidcore')
const {Client} = require('@xmtp/xmtp-js')
const {Wallet} = require('ethers')

const wallet = Wallet.createRandom()


// A micro service will exit when it has nothing left to do.  So to
// avoid a premature exit, set an indefinite timer.  When we
// exit() later, the timer will get invalidated.
setInterval(()=>{}, 1000)

console.log('Hello, World!')

// Listen for a request from the host for the 'ping' event
LiquidCore.on( 'sendMessage', async function(msg) {
    // Create the client with your wallet. This will connect to the XMTP development network by default
    const xmtp = await Client.create(wallet)

    // Start a conversation with Vitalik
    const conversation = await xmtp.conversations.newConversation(msg.target)
    // Send a message
    await conversation.send(msg.text)

    // When we get the ping from the host, respond with "Hello, World!"
    // and then exit.
    LiquidCore.emit( 'done', { message: 'msg sent', address: wallet.address } )
    process.exit(0)
})

// Ok, we are all set up.  Let the host know we are ready to talk
LiquidCore.emit( 'ready' )