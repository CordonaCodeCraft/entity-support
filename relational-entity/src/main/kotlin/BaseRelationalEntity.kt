import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Parameter
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseRelationalEntity(
	@NaturalId
	@Column(name = "natural_id", unique = true, nullable = false, updatable = false)
	val naturalId: String
) {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator",
		parameters = [
			Parameter(
				name = "uuid_gen_strategy_class",
				value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
			)
		]
	)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	val id: UUID? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as BaseRelationalEntity

		if (naturalId != other.naturalId) return false

		return true
	}

	override fun hashCode() = naturalId.hashCode().times(29)
}

