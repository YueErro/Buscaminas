package packModelo;

import java.util.*;

public class ArrayListOrdenado<T> implements List<T>
{
	private ArrayList<T> datos;
	private Comparator<T> comp;
	
    public ArrayListOrdenado( Comparator<T> pC )
    {
    	datos = new ArrayList<T>();
    	comp = pC;
    }
    
    public ArrayListOrdenado(Collection <? extends T> pCol,Comparator<T> pC)
    {
    	for( T elem: pCol )
    		datos.add( elem );
    	
    	comp =  pC;
    }	
    
    public boolean add( T pElement )
    {
    	if ( datos.isEmpty() )
    		return datos.add(pElement);

    	else
    	{
    		int i = 0;
    		
    		while( i < datos.size() && comp.compare( datos.get(i) , 
    													pElement ) < 0 )
    		{
    			i++;
    		}
    		
    		datos.add( i, pElement );
    		
    		return true;
    	}
    }
    
    public void setComparator( Comparator<T> pC )
    {
    	comp = pC;
    }
    
    public int binarySearch( T pElement ) 
    {
    	int low = 0;
    	int high = datos.size() - 1;
    	
    	while( high >= low )
    	{
    		int middle = ( low + high ) / 2;
    		
    		if( datos.get(middle).equals(pElement) )
    			return middle;

    		if( comp.compare( datos.get(middle), pElement ) < 0 )
    			low = middle + 1;
    		
    		if( comp.compare( datos.get(middle), pElement ) > 0 )
    			high = middle - 1;
    	}
    	return -1;
    }

    public String toString()
    {
    	return datos.toString();
    }
    
	@Override
	public void add(int arg0, T arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(int arg0) {
		// TODO Auto-generated method stub
		return datos.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T set(int arg0, T arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return datos.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}