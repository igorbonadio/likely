package br.com.stormcoders.likely

class LogProbability(v: Double) {
  val logValue = v
  def expValue = math.exp(-logValue)
  
  def *(p: LogProbability):LogProbability =
    new LogProbability(logValue + p.logValue)
    
  def /(p: LogProbability):LogProbability =
    new LogProbability(logValue - p.logValue)
    
  def +(p: LogProbability):LogProbability = 
    if (this > p)
      new LogProbability(logValue - math.log(1 + math.exp(logValue - p.logValue)))
    else if (this < p)
      p + this
    else // this == p
      new LogProbability(logValue - math.log(2))
    
  def -(p: LogProbability):LogProbability =
    new LogProbability(logValue - math.log(1 - math.exp(logValue - p.logValue)))

  def ==(p: LogProbability): Boolean =
    logValue == p.logValue

  def >(p: LogProbability): Boolean =
    logValue < p.logValue

  def >=(p: LogProbability): Boolean =
    logValue <= p.logValue

  def <(p: LogProbability): Boolean =
    logValue > p.logValue

  def <=(p: LogProbability): Boolean =
    logValue >= p.logValue
    
  override def toString:String = expValue.toString
}

object Probability {
  def apply(v: Double) = new LogProbability(-math.log(v))
}
