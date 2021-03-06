package br.com.stormcoders.likely

import org.scalatest._
import org.scalatest.matchers._

import Fancy._

class DiscreteDistributionSpec extends FlatSpec with ShouldMatchers {
  behavior of "A discrete distribution"

  trait Casino {
    val alphabet = Alphabet("Loaded", "Fair")
    val distribution = DiscreteDistribution(alphabet) { Prob =>
      Prob("Loaded") is 80.0.%%
      Prob("Fair") is 20.0.%%
    }
  }

  it should "break when I forget to define probabilities" in {
    val alphabet = Alphabet("Loaded", "Fair")
    val distribution = DiscreteDistribution(alphabet) { Prob =>
      Prob("Loaded") is 80.0.%%
    }
    distribution.prob(alphabet.id("Loaded")).expValue should be (0.8 plusOrMinus 0.0001)
    evaluating { distribution.prob(alphabet.id("Fair")).expValue } should produce [NullPointerException]
  }
  
  it should "get the probability of a given symbol" in new Casino {
    distribution.prob(alphabet.id("Loaded")).expValue should be (0.8 plusOrMinus 0.0001)
    distribution.prob(alphabet.id("Fair")).expValue should be (0.2 plusOrMinus 0.0001)
  }

  it should "choose a symbol" in  new Casino {
    distribution.choose should (be === 0 or be === 1)
  }

  it should "be trained by a stream of symbols" in new Casino {
    val d = DiscreteDistribution.train(
      alphabet.generateSequeceOfIds(Stream("Fair", "Fair", "Loaded", "Loaded", "Fair")))
    d.prob(alphabet.id("Loaded")).expValue should be (0.4 plusOrMinus 0.0001)
    d.prob(alphabet.id("Fair")).expValue should be (0.6 plusOrMinus 0.0001)
  }
}