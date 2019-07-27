# Java Technical Test
## Expectations
Treat this exercise as if it was a task you were implementing as part of a normal working day. In your submission you are expected to include everything you would commit to source control before signing off the task as production ready.

* No database or UI is required
* You can assume the code will only ever be executed in a single threaded environment
* Minimise the number of external jar dependencies your code has. We would expect a maximum of one or two would be required.
* All data to be in memory.
* Output format to be plain text, printed out to the console.
* Create more sample data as needed.
* We would expect you to spend somewhere in the region of about 3 hours on this exercise.

## The problem
Sample data represents the instructions sent by various clients to Bank to execute in the international market.

Entity | Buy/Sell | AgreedFx | Currency | InstructionDate | SettlementDate | Units | Price per unit
-------|-------|-------|-------|-------|-------	|-------|-------
foo | B | 0.50 | SGP | 01 Jan 2016 | 02 Jan 2016 | 200 | 100.25 
bar | S | 0.22 | AED | 05 Jan 2016 | 07 Jan 2016 | 450 | 150.5

* A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends Thursday. No other holidays to be taken into account.
* A trade can only be settled on a working day.
* If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day.
* USD amount of a trade = Price per unit * Units * Agreed Fx

## Requirements
Create a report that shows

* Amount in USD settled incoming everyday
* Amount in USD settled outgoing everyday
* Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing


## Glossary of terms:
* **Instruction:** An instruction to buy or sell
* **Entity:** A financial entity whose shares are to be bought or sold
* **Instruction Date:** Date on which the instruction was sent to JP Morgan by various clients
* **Settlement Date:** The date on which the client wished for the instruction to be settled with respect to Instruction Date
* **Buy/Sell flag:**
	*B – Buy – outgoing
	*S – Sell – incoming
* **Agreed Fx** is the foreign exchange rate with respect to USD that was agreed
* **Units:** Number of shares to be bought or sold
