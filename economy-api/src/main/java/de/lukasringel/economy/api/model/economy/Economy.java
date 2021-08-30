package de.lukasringel.economy.api.model.economy;

/**
 * This record represents an economy like dollar or euro.
 *
 * @param id                 - the unique database id of the economy
 * @param name               - the unique name of the economy
 * @param startValue         - the default worth of every account
 * @param increaseMultiplier - every increment gets multiplied by this value (default: 1.0)
 * @param decreaseMultiplier - every decrement gets multiplied by this value (default: 1.0)
 *
 */

public record Economy(int id,
                      String name,
                      double startValue,
                      double increaseMultiplier,
                      double decreaseMultiplier) {

    /**
     * This method computes the new increment value by our local increaseMultiplier
     *
     * @param amount - the amount by which the account worth should be increased
     * @return       - the computed result
     */
    public double increaseValueByMultiplier(double amount) {
        return amount * increaseMultiplier;
    }

    /**
     * This method computes the new decrement value by our local decreaseMultiplier
     *
     * @param amount - the amount by which the account worth should be decreased
     * @return       - the computed result
     */
    public double decreaseValueByMultiplier(double amount) {
        return amount * decreaseMultiplier;
    }

}
