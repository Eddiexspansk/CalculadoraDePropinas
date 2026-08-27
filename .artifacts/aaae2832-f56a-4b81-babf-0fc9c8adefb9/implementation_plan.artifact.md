# Implementation Plan - Edit Waiter Functionality

This plan outlines the changes required to allow users to edit existing waiters in the list.

## Proposed Changes

### Domain Layer

#### [MODIFY] [CamareroRepository.kt](file:///C:/Users/spans/StudioProjects/TipApp/app/src/main/java/com/developerCompany/calculadoraDePropinas/domain/repository/CamareroRepository.kt)
- Add `suspend fun updateCamarero(camarero: Camarero)` to the interface.

---

### Data Layer

#### [MODIFY] [CamareroRepositoryImpl.kt](file:///C:/Users/spans/StudioProjects/TipApp/app/src/main/java/com/developerCompany/calculadoraDePropinas/data/repository/CamareroRepositoryImpl.kt)
- Implement `updateCamarero(camarero: Camarero)` by calling `dao.insertCamarero(camarero)` (which uses `REPLACE` strategy).

---

### UI Layer

#### [MODIFY] [CamareroViewModel.kt](file:///C:/Users/spans/StudioProjects/TipApp/app/src/main/java/com/developerCompany/calculadoraDePropinas/ui/main/CamareroViewModel.kt)
- Update `CalcUiState` to include `val camareroEnEdicion: Camarero? = null`.
- Add `iniciarEdicion(camarero: Camarero)` to set the waiter to be edited.
- Add `cancelarEdicion()` to clear the edit state.
- Add `actualizarCamarero(camarero: Camarero)` to save changes to the database.

#### [MODIFY] [CalcScreen.kt](file:///C:/Users/spans/StudioProjects/TipApp/app/src/main/java/com/developerCompany/calculadoraDePropinas/ui/main/CalcScreen.kt)
- Update `CalcScreen` to react to `uiState.camareroEnEdicion`.
- When a waiter is being edited:
    - Pre-fill `nombre` and `horas` fields.
    - Change the "Add" button text to "Update Waiter" (or similar).
    - Provide a way to cancel editing.
- Add an "Edit" icon button to `CamareroItem`.

## Verification Plan

### Manual Verification
1.  Add a waiter.
2.  Click the "Edit" button on the newly added waiter.
3.  Verify the input fields are populated correctly.
4.  Change the name or hours.
5.  Click "Update Waiter".
6.  Verify the list updates with the new data.
7.  Verify the "Value per Hour" and individual tips are recalculated correctly.
8.  Verify that clicking "Clear" or "Add" during editing works as expected (e.g., clears edit mode).
