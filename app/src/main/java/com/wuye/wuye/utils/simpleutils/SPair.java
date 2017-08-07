package com.wuye.wuye.utils.simpleutils;

import android.support.v4.util.Pair;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/3/30.
 */

public class SPair<F, S> extends Pair<F, S> implements Serializable {

    public final S third;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public SPair(F first, S second) {
        super(first, second);
        this.third = null;
    }

    public SPair(F first, S second, S third) {
        super(first, second);
        this.third = third;
    }


}
