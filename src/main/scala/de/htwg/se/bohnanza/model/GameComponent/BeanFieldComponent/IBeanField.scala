package de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

trait IBeanField(val bean: Option[Bean], val quantity: Int) {

  /* Converts the BeanField to a string representation */
  override def toString: String

  /* Harvests the bean field, converting the beans into coins based on their
     defined conversion rates. Returns the number of coins harvested and an
     updated BeanField with no beans. */
  def harvestField(): (Int, IBeanField)

  /* Plants a bean into the bean field. If the field already contains the same
     type of bean, increases the quantity by 1. Otherwise, initializes the field
     with the new bean type and a quantity of 1. Returns the updated BeanField. */
  def plantToField(beanToPlant: Bean): IBeanField

  /* Creates a copy of the beanfield */
  def copy(
      bean: Option[Bean] = this.bean,
      quantity: Int = this.quantity
  ): IBeanField
}
