# crypto-currency

Write a RESTful cryptocurrency portfolio tracking service that allows the user to add, remove
and list various cryptocurrencies and tracks their current market value.
Functional requirements
• Only the backend is required, client/frontend should be emulated by tests
• No authentication or users are needed, assume the service is only accessible by
yourself
• Use any relational database of your choice (PostgreSQL, MySQL, H2, etc...)
• Use Bitfinex's public API to calculate the current market value and display it in euros.
The API documentation can be found here: https://docs.bitfinex.com/reference
• The list of available symbols (tickers) can be found here:
https://docs.bitfinex.com/v1/reference#rest-public-symbols
• The current market value should be calculated for each entry separately.
• The service should provide at least the following API methods:
o adding a new entry
o deleting an existing entry
o updating an existing entry
o getting a specific entry
o listing all entries

The request for adding an entry should contain the following fields:
• Cryptocurrency name
• Amount purchased
• Wallet location (just String, can be anything the user inputs, e.g. "Bitstamp",
"Hardware wallet", "Bitfinex", etc)
The response for a single entry should contain the following fields:
• ID
• Cryptocurrency name
• Amount purchased
• Datetime of entry creation
• Wallet location
• Market value at the time of purchase
• Current market value
An example of adding a new portfolio item
I made a purchase of 10 Bitcoins on 20.01.2021
• As a user, I can make a request to add 10 Bitcoins
• The system saves the portfolio item in the database. The current market value is
calculated by the current market price. The current price is queried from Bitfinex's API
and market values calculated by the backend.
Database structure and the data to store in it is up to you to decide.
