package com.spe.applicative;

import com.spe.functor.Functor;

import java.util.function.Function;

/**
 * An interface representing applicative functors - functors that can have their results combined with other functors
 * of the same instance in a context-free manner.
 * <p>
 * The same rules that apply to <code>Functor</code> apply to <code>Applicative</code>, along with the following
 * additional 4 laws:
 * <ul>
 * <li>identity: <code>v.zip(pureId).equals(v)</code></li>
 * <li>composition: <code>w.zip(v.zip(u.zip(pureCompose))).equals((w.zip(v)).zip(u))</code></li>
 * <li>homomorphism: <code>pureX.zip(pureF).equals(pureFx)</code></li>
 * <li>interchange: <code>pureY.zip(u).equals(u.zip(pure(f -&gt; f.apply(y))))</code></li>
 * </ul>
 * As with <code>Functor</code>, <code>Applicative</code> instances that do not satisfy all of the functor laws, as well
 * as the above applicative laws, are not well-behaved and often break down in surprising ways.
 * <p>
 * For more information, read about
 * <a href="https://en.wikipedia.org/wiki/Applicative_functor" target="_top">Applicative Functors</a>.
 *
 * @param <A>   The type of the parameter
 * @param <App> The unification parameter to more tightly type-constrain Applicatives to themselves
 */
public interface Applicative<A, App extends Applicative> extends Functor<A, App> {

    /**
     * Lift the value <code>b</code> into this applicative functor.
     *
     * @param b   the value
     * @param <B> the type of the returned applicative's parameter
     * @return an instance of this applicative over b
     */
    <B> Applicative<B, App> pure(B b);

    /**
     * Given another instance of this applicative over a mapping function, "zip" the two instances together using
     * whatever application semantics the current applicative supports.
     *
     * @param appFn the other applicative instance
     * @param <B>   the resulting applicative parameter type
     * @return the mapped applicative
     */
    <B> Applicative<B, App> apply(Applicative<Function<? super A, ? extends B>, App> appFn);

    @Override
    default <B> Applicative<B, App> fmap(Function<? super A, ? extends B> fn) {
        return apply(pure(fn));
    }

}
