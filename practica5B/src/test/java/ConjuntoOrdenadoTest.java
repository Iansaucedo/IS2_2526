	
	import static org.assertj.core.api.Assertions.assertThat;
	import static org.assertj.core.api.Assertions.assertThatThrownBy;
	
	import org.junit.Before;
	import org.junit.Test;
	
	import es.unican.is2.ConjuntoOrdenado;
	
	public class ConjuntoOrdenadoTest {
	
	    private ConjuntoOrdenado<Integer> conjunto;
	
	    @Before
	    public void setUp() {
	        conjunto = new ConjuntoOrdenado<>();
	    }
	
	    // ---------- SIZE ----------
	
	    @Test
	    public void testInitialState() {
	        assertThat(conjunto.size()).isEqualTo(0);
	    }
	
	    @Test
	    public void testSizeAfterAdd() {
	        conjunto.add(1);
	        conjunto.add(2);
	        assertThat(conjunto.size()).isEqualTo(2);
	    }
	
	    @Test
	    public void testSizeAfterRemove() {
	        conjunto.add(1);
	        conjunto.remove(0);
	        assertThat(conjunto.size()).isEqualTo(0);
	    }
	
	    // ---------- ADD ----------
	
	    @Test
	    public void testAddValidElements() {
	        assertThat(conjunto.add(5)).isTrue();
	        assertThat(conjunto.add(3)).isTrue();
	        assertThat(conjunto.add(8)).isTrue();
	        assertThat(conjunto.size()).isEqualTo(3);
	    }
	
	    @Test
	    public void testAddDuplicate() {
	        assertThat(conjunto.add(5)).isTrue();
	        assertThat(conjunto.add(5)).isFalse();
	        assertThat(conjunto.size()).isEqualTo(1);
	    }
	
	    @Test
	    public void testAddNull() {
	        assertThatThrownBy(() -> conjunto.add(null))
	            .isInstanceOf(NullPointerException.class);
	    }
	
	    @Test
	    public void testAddNegativeNumbers() {
	        conjunto.add(-5);
	        conjunto.add(-3);
	        conjunto.add(-8);
	
	        assertThat(conjunto.size()).isEqualTo(3);
	    }
	    
		 @Test
		 public void testAddNegativeNumbersOrdered() {
		     conjunto.add(-5);
		     conjunto.add(-3);
		     conjunto.add(-8);

		     assertThat(conjunto.get(0)).isEqualTo(-8);
		     assertThat(conjunto.get(1)).isEqualTo(-5);
		     assertThat(conjunto.get(2)).isEqualTo(-3);
		 }

	
	    
	    
	    // ---------- GET ----------
	
	    @Test
	    public void testGetValidPositions() {
	        conjunto.add(3);
	        conjunto.add(5);
	        conjunto.add(8);
	
	        assertThat(conjunto.get(0)).isNotNull();
	        assertThat(conjunto.get(1)).isNotNull();
	        assertThat(conjunto.get(2)).isNotNull();
	    }
	
	    @Test
	    public void testGetFirstAndLast() {
	        conjunto.add(1);
	        conjunto.add(2);
	
	        assertThat(conjunto.get(0)).isNotNull();
	        assertThat(conjunto.get(conjunto.size() - 1)).isNotNull();
	    }
	    
	    @Test
	    public void testGetLastElementValue() {
	        conjunto.add(1);
	        conjunto.add(2);
	
	        Integer last = conjunto.get(conjunto.size() - 1);
	        assertThat(last).isEqualTo(2); 
	    }
	
	    @Test
	    public void testGetNegativeIndex() {
	        assertThatThrownBy(() -> conjunto.get(-1))
	            .isInstanceOf(IndexOutOfBoundsException.class);
	    }
	
	    @Test
	    public void testGetIndexOutOfBounds() {
	        conjunto.add(1);
	
	        assertThatThrownBy(() -> conjunto.get(1))
	            .isInstanceOf(IndexOutOfBoundsException.class);
	    }
	    
		 @Test
		 public void testGetValuesInOrder() {
		     conjunto.add(2);
		     conjunto.add(1);
		     conjunto.add(3);

		     assertThat(conjunto.get(0)).isEqualTo(1);
		     assertThat(conjunto.get(1)).isEqualTo(2);
		     assertThat(conjunto.get(2)).isEqualTo(3);
		 }
	
	    // ---------- REMOVE ----------
	
	    @Test
	    public void testRemoveValid() {
	        conjunto.add(3);
	        conjunto.add(5);
	        conjunto.add(8);
	
	        Integer removed = conjunto.remove(1);
	        assertThat(removed).isNotNull();
	        assertThat(conjunto.size()).isEqualTo(2);
	    }
	
	    @Test
	    public void testRemoveFirst() {
	        conjunto.add(1);
	        conjunto.add(2);
	
	        Integer removed = conjunto.remove(0);
	        assertThat(removed).isNotNull();
	        assertThat(conjunto.size()).isEqualTo(1);
	    }
	
	    @Test
	    public void testRemoveLast() {
	        conjunto.add(1);
	        conjunto.add(2);
	
	        Integer removed = conjunto.remove(conjunto.size() - 1);
	        assertThat(removed).isNotNull();
	        assertThat(conjunto.size()).isEqualTo(1);
	    }
	
	    @Test
	    public void testRemoveNegativeIndex() {
	        assertThatThrownBy(() -> conjunto.remove(-1))
	            .isInstanceOf(IndexOutOfBoundsException.class);
	    }
	
	    @Test
	    public void testRemoveOutOfBounds() {
	        conjunto.add(1);
	
	        assertThatThrownBy(() -> conjunto.remove(1))
	            .isInstanceOf(IndexOutOfBoundsException.class);
	    }
	
		 @Test
		 public void testRemoveReturnsCorrectElement() {
		     conjunto.add(3);
		     conjunto.add(5);
		     conjunto.add(8);

		     Integer removed = conjunto.remove(1);
		     assertThat(removed).isEqualTo(5);
		 }

		 @Test
		 public void testRemoveUntilEmpty() {
		     conjunto.add(1);
		     conjunto.add(2);

		     conjunto.remove(0);
		     conjunto.remove(0);

		     assertThat(conjunto.size()).isEqualTo(0);
		 }
		 
	    // ---------- CLEAR ----------
	
	    @Test	
	    public void testClear() {
	        conjunto.add(1);
	        conjunto.add(2);
	        conjunto.clear();
	
	        assertThat(conjunto.size()).isEqualTo(0);
	    }
	
	    @Test
	    public void testClearThenAdd() {
	        conjunto.add(1);
	        conjunto.clear();
	        conjunto.add(2);
	
	        assertThat(conjunto.size()).isEqualTo(1);
	    }
	    
	    // ---------- ORDERING ----------
	    @Test
	    public void testNaturalOrder() {
	        conjunto.add(5);
	        conjunto.add(3);
	        conjunto.add(8);
	
	        assertThat(conjunto.get(0)).isEqualTo(3);
	        assertThat(conjunto.get(1)).isEqualTo(5);
	        assertThat(conjunto.get(2)).isEqualTo(8);
	    }
	    
	    @Test
	    public void testMixedNumbersOrder() {
	        conjunto.add(5);
	        conjunto.add(-2);
	        conjunto.add(3);
	        conjunto.add(-10);

	        assertThat(conjunto.get(0)).isEqualTo(-10);
	        assertThat(conjunto.get(1)).isEqualTo(-2);
	        assertThat(conjunto.get(2)).isEqualTo(3);
	        assertThat(conjunto.get(3)).isEqualTo(5);
	    }


	 @Test
	 public void testGetLastIndexExplicit() {
	     conjunto.add(10);
	     conjunto.add(20);
	     conjunto.add(30);

	     assertThat(conjunto.get(2)).isEqualTo(30); // size-1 explícito
	 }
	 

	}
	
