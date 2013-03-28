package br.com.igorbonadio.likely

import org.scalatest._
import org.scalatest.matchers._

class DiscreteIIDModelSpec extends FlatSpec with ShouldMatchers {
  behavior of "A Discrete IID Model"
  
  it should "get the probability of a given sequence" in {
    val alphabet = new Alphabet(List("Loaded", "Fair"))
    val distribution = new DiscreteDistribution(List(Probability(0.8), Probability(0.2)))
    val model = new DiscreteIIDModel(distribution)
    val sequence = alphabet.generateSequeceOfIds(List("Fair", "Loaded"))
    
    model.prob(sequence).expValue should be (0.16 plusOrMinus 0.0001)
  }

  it should "get the probability of a given stream" in {
    val alphabet = new Alphabet(List("Loaded", "Fair"))
    val distribution = new DiscreteDistribution(List(Probability(0.8), Probability(0.2)))
    val model = new DiscreteIIDModel(distribution)
    val sequence = alphabet.generateSequeceOfIds(Stream("Fair", "Loaded"))
    
    model.prob(sequence).expValue should be (0.16 plusOrMinus 0.0001)
  }
  
  it should "generate a random symbol" in {
    val alphabet = new Alphabet(List("Loaded", "Fair"))
    val distribution = new DiscreteDistribution(List(Probability(0.8), Probability(0.2)))
    val model = new DiscreteIIDModel(distribution)
    
    model.choose should (be === 0 or be === 1)
  }
  
  it should "generate a random sequence" in {
    val alphabet = new Alphabet(List("Loaded", "Fair"))
    val distribution = new DiscreteDistribution(List(Probability(0.8), Probability(0.2)))
    val model = new DiscreteIIDModel(distribution)
    
    model.chooseSequence(5).foreach(sym => sym should (be === 0 or be === 1))
  }
}

class ContinuousIIDModelSpec extends FlatSpec with ShouldMatchers {
  behavior of "A Continuous IID Model"
  
  it should "get the probability of a given sequence" in {
    val distribution = new NormalDistribution(0, 1)
    val model = new ContinuousIIDModel(distribution)
    val sequence = List(0.1, 0.2, 0.3, 0.4)
    
    model.prob(sequence).expValue should be (0.0218 plusOrMinus 0.0001)
  }

  it should "get the probability of a given stream" in {
    val distribution = new NormalDistribution(0, 1)
    val model = new ContinuousIIDModel(distribution)
    val sequence = Stream(0.1, 0.2, 0.3, 0.4)
    
    model.prob(sequence).expValue should be (0.0218 plusOrMinus 0.0001)
  }
  
  it should "generate a random symbol" in {
    val distribution = new NormalDistribution(0, 1)
    val model = new ContinuousIIDModel(distribution)
    
    model.choose should not be === (model.choose)
  }
  
  it should "generate a random sequence" in {
    val distribution = new NormalDistribution(0, 1)
    val model = new ContinuousIIDModel(distribution)
    
    model.chooseSequence(5).length should be === (5)
  }
  
}