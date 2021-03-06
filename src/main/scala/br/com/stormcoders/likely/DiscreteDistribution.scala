package br.com.stormcoders.likely

import util.Random

import Fancy._

class DiscreteDistribution(probabilities: List[LogProbability]) extends Distribution[Int] {
  val probabilityMap = ((0 to (probabilities.length - 1)) zip probabilities).toMap
  
  def prob(id: Int):LogProbability = probabilityMap(id)

  def choose : Int = {
    def chooseWith(v: Double, prob: List[LogProbability], sym: Int): Int = prob match {
      case p::ps => if ((v - p.expValue) < 0) sym
              else chooseWith(v - p.expValue, ps, sym + 1)
      case List() => sym
    }
    chooseWith((new Random()).nextDouble, probabilities, 0)
  }
}

object DiscreteDistribution extends DistributionObject[Int] {
  def apply(probabilities: LogProbability*) =
    new DiscreteDistribution(probabilities.toList)

  def apply(alphabet: Alphabet)(definition: (ProbabilityOf => Unit)): DiscreteDistribution = {
    var probs = new ProbabilityOf(alphabet)
    definition(probs)
    probs.distribution
  }
    
  def train(sequence: Stream[Int]): DiscreteDistribution =
    new DiscreteDistribution(
      sequence.groupBy(x => x).toSeq.sortBy(_._1).map { case (k, v) => 
        Probability(v.length.toDouble/sequence.length) }.toList)
}